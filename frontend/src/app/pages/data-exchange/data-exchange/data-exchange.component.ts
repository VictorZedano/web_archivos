// src/app/data-exchange/data-exchange.component.ts

import { Component, OnInit, OnDestroy, inject } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
// Importamos el servicio manteniendo el nombre original del archivo
import { DataExchangeService } from '../../../core/services/data-exchange.service';
import { TransactionRecord } from '../../../core/models/transaction-record.model';

// Importaciones de RxJS para el Polling (Sondeo)
import { Subscription, interval, switchMap, takeWhile, catchError, of, tap } from 'rxjs';


@Component({
  selector: 'app-data-exchange',
  standalone: true,
  imports: [CommonModule, DatePipe],
  templateUrl: './data-exchange.component.html',
  styleUrls: ['./data-exchange.component.css']
})
export class DataExchangeComponent implements OnInit, OnDestroy {
  // Usamos la clase original pero la variable en español
  private servicioDatos = inject(DataExchangeService);

  // --- PROPIEDADES DE POLLING (SONDEO) Y ESTADO ---
  private suscripcionSondeo!: Subscription;
  private readonly INTERVALO_SONDEO = 5000; // 5 segundos

  // Propiedades en español
  historial: TransactionRecord[] = [];
  archivoSeleccionado: File | null = null;
  cargando: boolean = false;
  mensajeCarga: string = '';
  mensajeError: string = '';

  ngOnInit(): void {
    // Iniciamos el sondeo para cargar el historial y monitorear procesos
    this.iniciarSondeo();
  }

  /**
   * CRÍTICO: Limpia la suscripción del sondeo para evitar fugas de memoria.
   */
  ngOnDestroy(): void {
    if (this.suscripcionSondeo) {
      this.suscripcionSondeo.unsubscribe();
    }
  }

  /**
   * Inicia el proceso de Sondeo (Polling) para refrescar el historial periódicamente.
   */
  iniciarSondeo(): void {
    // Detener suscripción previa si existe
    if (this.suscripcionSondeo && !this.suscripcionSondeo.closed) {
      this.suscripcionSondeo.unsubscribe();
    }

    this.suscripcionSondeo = interval(this.INTERVALO_SONDEO).pipe(
      // Utilizamos el método obtenerHistorial del servicio
      switchMap(() => this.servicioDatos.obtenerHistorial().pipe(
        catchError(err => {
          console.error('Error durante el Sondeo de historial', err);
          this.mensajeError = 'Fallo en la conexión durante el monitoreo de procesos.';
          return of(this.historial);
        })
      )),
      tap(registros => {
        // Asignación y ordenación por fecha de forma descendente
        this.historial = registros.sort((a, b) => new Date(b.uploadDate).getTime() - new Date(a.uploadDate).getTime());
      }),
      // Condición de parada del Sondeo: detener si no hay registros pendientes
      takeWhile(registros => {
        const tienePendientes = registros.some(r => r.status === 'EN_PROCESO');

        if (!tienePendientes) {
          if (this.mensajeCarga && this.mensajeCarga.includes('ha comenzado')) {
            this.mensajeCarga = '✅ Todos los procesos de carga han finalizado.';
          }
          return false; // Detiene el stream
        }
        return true; // Continúa el stream
      }, true)
    ).subscribe({
      complete: () => {
        console.log('Sondeo de historial finalizado.');
      }
    });
  }

  /**
   * Maneja la selección del archivo por el usuario.
   */
  onArchivoSeleccionado(evento: Event): void {
    const input = evento.target as HTMLInputElement;
    this.mensajeError = '';
    this.archivoSeleccionado = null;

    if (input.files && input.files.length > 0) {
      const archivo = input.files[0];
      const extensionArchivo = archivo.name.split('.').pop()?.toLowerCase();

      // Validación de extensión (solo .xlsx o .xls)
      if (extensionArchivo === 'xlsx' || extensionArchivo === 'xls') {
        this.archivoSeleccionado = archivo;
        this.mensajeCarga = `Archivo seleccionado: ${archivo.name} (${(archivo.size / 1024 / 1024).toFixed(2)} MB)`;
      } else {
        this.mensajeError = 'Tipo de archivo no permitido. Por favor, selecciona un archivo Excel (.xlsx o .xls).';
        input.value = '';
      }
    }
  }


  /**
   * Inicia el proceso de carga del archivo al servidor.
   */
  subirArchivo(): void {
    if (!this.archivoSeleccionado) {
      this.mensajeError = 'Por favor, selecciona un archivo antes de iniciar la carga.';
      return;
    }

    this.cargando = true;
    this.mensajeError = '';

    // Usamos el método de servicio con nombre en español
    this.servicioDatos.subirArchivo(this.archivoSeleccionado).subscribe({
      next: () => {
        this.mensajeCarga = `¡Archivo ${this.archivoSeleccionado!.name} cargado con éxito! El procesamiento ha comenzado.`;
        this.archivoSeleccionado = null;
        // Inicia o reinicia el sondeo para actualizar el historial de inmediato
        this.iniciarSondeo();
      },
      error: (err) => {
        this.mensajeError = `Error al procesar la carga: ${err.error?.message || 'Error desconocido del servidor.'}`;
      },
      complete: () => {
        this.cargando = false;
      }
    });
  }

  /**
   * Exporta el historial de carga de la data (el log de transacciones).
   */
  exportarLogHistorial(): void {
    this.servicioDatos.exportarLogHistorial().subscribe({
      next: (blob) => {
        this.guardarArchivo(blob, `Log_Transacciones_${new Date().toISOString().slice(0, 10)}.xlsx`);
        this.mensajeCarga = 'Exportación del historial de cargas iniciada.';
      },
      error: (err) => {
        this.mensajeError = `Error al exportar el historial: ${err.error?.message || 'Error desconocido.'}`;
      }
    });
  }

  /**
   * Exporta TODOS los registros clínicos de la base de datos.
   */
  exportarDatosClinicos(): void {
    this.servicioDatos.exportarDatosClinicos().subscribe({
      next: (blob) => {
        this.guardarArchivo(blob, `Data_Clinica_Completa_${new Date().toISOString().slice(0, 10)}.xlsx`);
        this.mensajeCarga = 'Exportación de la data clínica completa iniciada.';
      },
      error: (err) => {
        this.mensajeError = `Error al exportar los datos clínicos: ${err.error?.message || 'Error desconocido.'}`;
      }
    });
  }

  /**
   * Descarga el archivo de errores de un registro específico.
   */
  descargarArchivoError(idRegistro: number, nombreArchivoOriginal: string): void {
    this.servicioDatos.descargarErrores(idRegistro).subscribe(blob => {
      const nombreArchivoError = `Errores_${nombreArchivoOriginal}`;
      this.guardarArchivo(blob, nombreArchivoError);
    });
  }

  /**
   * Función auxiliar para guardar el Blob como un archivo en el navegador.
   */
  private guardarArchivo(blob: Blob, nombreArchivo: string): void {
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = nombreArchivo;
    document.body.appendChild(a);
    a.click();
    window.URL.revokeObjectURL(url);
    a.remove();
  }
}