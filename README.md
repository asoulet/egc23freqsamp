# Echantillonnage de motifs avec une contrainte de fréquence

L'échantillonnage de motifs est une technique récente de découverte de motifs favorisant l'interactivité avec l'utilisateur. Son principe est de tirer aléatoirement un motif proportionnellement à son intérêt. Malheureusement, les tirages peuvent se focaliser sur une partie de l'espace de recherche avec des motifs peu fréquents mais extrêmement nombreux. Il serait bien possible d'échantillonner des motifs et d'éliminer ceux non-fréquents, mais le taux de rejet s'avère souvent trop élevé. Dans cet article, nous proposons la première méthode efficace d'échantillonnage de motifs avec une contrainte de fréquence minimale. Elle s'appuie sur la suppression des items non-fréquents (opération de réduction) et sur la projection de la base de données sur chacun des items (opération de projection). En combinant ces deux opérations, nous proposons une méthode générique qui revient à éliminer tous les motifs contenant un couple non-fréquent d'items. Nos expérimentations montrent que notre méthode réduit considérablement le taux de rejet.

## Publication

*Echantillonnage de motifs avec une contrainte de fréquence.* [Arnaud Soulet](https://www.info.univ-tours.fr/~soulet/), Full paper at [EGC23](https://egc2023.sciencesconf.org/)

## Results of experiments

We provide the results of all our experiments in this CSV file: [experimental_results.csv](results/experimental_results.csv)

## Source code

We provide the Java source code of the prototype in `src/` directory.

