% Caso base: cuando el número llega a 0, la lista resultante es vacía.
almacenar(0, []).

% Caso recursivo: extrae el último dígito con 'mod 10',
% y construye la lista de forma recursiva.
almacenar(N, [D | T]) :-
    N > 0,
    D is N mod 10,       
    N1 is N // 10,       
    almacenar(N1, T).
