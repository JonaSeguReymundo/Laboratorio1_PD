% ===============================================
% REGLAS DERIVADAS
% ===============================================

:- consult('hechos.pl').

% 1. zona_segura/1
% Propósito: Identifica si una zona carece totalmente de enemigos 
% y no está bloqueada por llaves.
% Consulta SWI-Prolog: ?- zona_segura(Zona).
zona_segura(Zona) :-
    zona(Zona),
    \+ enemigo(_, Zona),
    \+ requiere_llave(Zona).

% 2. puede_construir/1
% Propósito: Determina si un personaje P puede realizar construcciones 
% basándose en si existen troncos en la superficie y si P posee un hacha 
% o tiene la habilidad nativa de construir.
% Consulta SWI-Prolog: ?- puede_construir(eric). / ?- puede_construir(kelvin).
puede_construir(P) :-
    recurso(troncos, superficie),
    (posee(P, hacha) ; habilidad(P, construir)).

% 3. necesita_equipo_completo/3
% Propósito: Verifica si adentrarse a una zona en determinado momento del día 
% presenta un nivel de peligro alto y si el personaje P cuenta con hacha y encendedor.
% Consulta SWI-Prolog: ?- necesita_equipo_completo(eric, cuevas, dia).
necesita_equipo_completo(P, Zona, Tiempo) :-
    (peligro(Zona, alto, Tiempo) ; peligro(Zona, alto, todo_momento)),
    posee(P, hacha),
    posee(P, encendedor).