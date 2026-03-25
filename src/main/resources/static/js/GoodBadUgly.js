// Crear un contexto de audio
const audioContext = new (window.AudioContext || window.webkitAudioContext)();

// Función para reproducir una nota con frecuencia y duración
function playNote(frequency, duration, startTime) {
	const oscillator = audioContext.createOscillator();
	oscillator.frequency.setValueAtTime(frequency, audioContext.currentTime);
	oscillator.type = 'sine'; // Tipo de onda (senoidal)

	// Conectar el oscilador al destino (altavoces)
	oscillator.connect(audioContext.destination);

	// Iniciar y detener el oscilador con el tiempo adecuado
	oscillator.start(startTime);
	oscillator.stop(startTime + duration);
}

// Secuencia de notas con sus frecuencias y duraciones
const BGU = [
	{ frequency: 440, duration: 0.125 },
	{ frequency: 587, duration: 0.125 },
	{ frequency: 440, duration: 0.125 },
	{ frequency: 587, duration: 0.125 },
	{ frequency: 440, duration: 1 },
	{ frequency: 0, duration: 0.5 },
	{ frequency: 349, duration: 0.5 },
	{ frequency: 392, duration: 0.5 },
	{ frequency: 294, duration: 1 },
	{ frequency: 0, duration: 0.5 },
	{ frequency: 440, duration: 0.125 },
	{ frequency: 587, duration: 0.125 },
	{ frequency: 440, duration: 0.125 },
	{ frequency: 587, duration: 0.125 },
	{ frequency: 440, duration: 1 },
	{ frequency: 0, duration: 0.5 },
	{ frequency: 349, duration: 0.5 },
	{ frequency: 392, duration: 0.5 },
	{ frequency: 523, duration: 1 },
	{ frequency: 0, duration: 0.5 },
	{ frequency: 440, duration: 0.125 },
	{ frequency: 587, duration: 0.125 },
	{ frequency: 440, duration: 0.125 },
	{ frequency: 587, duration: 0.125 },
	{ frequency: 440, duration: 1 },
	{ frequency: 0, duration: 0.5 },
	{ frequency: 349, duration: 0.5 },
	{ frequency: 329, duration: 0.25 },
	{ frequency: 294, duration: 0.25 },
	{ frequency: 261, duration: 1 },
	{ frequency: 0, duration: 0.5 },
	{ frequency: 440, duration: 0.125 },
	{ frequency: 587, duration: 0.125 },
	{ frequency: 440, duration: 0.125 },
	{ frequency: 587, duration: 0.125 },
	{ frequency: 440, duration: 1 },
	{ frequency: 0, duration: 0.5 },
	{ frequency: 440, duration: 0.5 },
	{ frequency: 392, duration: 0.5 },
	{ frequency: 294, duration: 1 },
];

// Función para reproducir la secuencia de notas con los tiempos adecuados
function playSequence() {
	let currentTime = audioContext.currentTime;

	// Reproducir las notas en la secuencia, respetando los tiempos de inicio
	BGU.forEach(note => {
		if (note.frequency === 0) {
			// Pausa sin sonido, solo esperar el tiempo
			currentTime += note.duration;
		} else {
			// Reproducir la nota con el tiempo adecuado
			playNote(note.frequency, note.duration, currentTime);
			currentTime += note.duration; // Ajustar el tiempo de inicio de la siguiente nota
		}
	});
}

// Reproducir la secuencia
playSequence();
