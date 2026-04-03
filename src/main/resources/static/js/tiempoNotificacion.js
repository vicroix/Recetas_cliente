document.addEventListener("DOMContentLoaded", function() {
    const notificacion = document.querySelector(".alerta-temp");
    if (notificacion) {
        setTimeout(() => {
            notificacion.style.transition = "opacity 0.8s ease";
            notificacion.style.opacity = "0";
            setTimeout(() => {
                notificacion.remove();
            }, 800);

        }, 3000);
    }
});