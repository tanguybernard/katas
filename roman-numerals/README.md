Roman Numerals

https://kata-log.rocks/roman-numerals-kata


L'idée que j'ai eu (14 oct 2024)

Je commence par 1, 2, 3, 4, 5 et 12.
J'écris une HashMap je remplis I, IV, V, X et je me rends compte qu'il faut que je fasse une concaténation.
Je décide d'utiliser une TreeMap qui me permet d'ordonner et de chercher la clé qui a pour valeur égale ou inférieur à celle que je cherche.

Exemple je cherche 6 alors on me donne le 5 (V) puis je soustrait et je cherche 1 (I) donc 6 (VI).

J'essaye d'utiliser le property based testing avec des liste d'inputs associés à des resultats fixe (1 => I, 14 => XIV)...

Puis j'essaye de trouver un système plus random en vérifiant ce qui ne doit pas arriver comme MMMM car 4000 ne peut pas se produire.

Il faudrait que j'enrichisse se dernier point pour avoir de meilleurs vérifications.


## Pour plus tard :  Property Based Testing


-  // quelque soit n (entre 1 et 3000) le result contiendra IVXLMC
-  // ...5, 50, 500, V L, D ne repete pas; une seul occurence
-  // 1 meme symbole repetable, pas plus de 3 consecutif
-  // au plus 3 repet d'une meme symbole
-  // au plus 3 repete consecutive
-  //quelque soit n entre 1 et 3000 quand je trouve plusieur occ de I c'est toujours a la fin

  
