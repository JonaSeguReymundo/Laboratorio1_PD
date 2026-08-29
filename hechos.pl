% ===============================================
% BHECHOS 
% ===============================================

% Personajes (Nombre, Rol/Tipo, Edad)
personaje(eric, protagonista, 30).
personaje(timmy, secundario, desconocido).
personaje(kelvin, aliado_capturado, desconocido).
personaje(virginia, mutante_aliada, desconocido).

% Capacidades y estado
habla(kelvin, no).

habilidad(kelvin, cargar_troncos).
habilidad(kelvin, construir).

posee(eric, hacha).
posee(eric, encendedor).

% Necesidades vitales
necesita(eric, refugio).
necesita(eric, comida).
necesita(eric, agua).

% Zonas de la isla
zona(superficie).
zona(cuevas).
zona(bunkeres).

% Enemigos por zona
enemigo(canibales, superficie).
enemigo(mutantes, superficie).
enemigo(mutantes, cuevas).

% Mecánicas de zonas
requiere_llave(bunkeres).

% Nivel de peligro por zona y tiempo
peligro(cuevas, alto, todo_momento).
peligro(superficie, medio, dia).
peligro(superficie, alto, noche).

% Recursos y su ubicación
recurso(troncos, superficie).
recurso(piedras, superficie).