% Ejercicio 2: Suma N con todos los números anteriores hasta llegar a 1.

% Caso base
suma_n(1, 1).

% Caso recursivo
suma_n(N, R) :-
    N > 1,
    N1 is N - 1,
    suma_n(N1, R1),
    R is N + R1.
