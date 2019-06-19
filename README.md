# Android Test Rappi

## Test description
```
El primer paso del proceso de selección consiste en superar esta prueba, está diseñada para evaluar tu conocimiento y experiencia como desarrollador Android.

Por favor ten en cuenta lo siguiente:

- Consumir el API de películas y series de la siguiente pagina: https://developers.themoviedb.org/4/getting-started/authorization

- Debe tener tres categorías de películas y/o series: Popular, Top Rated, Upcoming.
- Cada película y/o series debe poder visualizar su detalle.
- Debe funcionar tanto online como offline (cache).
- Debe tener un buscador offline por categorías.


Valoraciones extras:

1. Visualización de los videos en el detalle de cada ítem.
2. Transiciones, Animaciones, UI/UX.
3. Buscador Online.
4. Pruebas Unitarias


Una vez acabada la prueba describa en un readme brevemente:

1. Las capas de la aplicación (por ejemplo capa de persistencia, vistas, red, negocio, etc) y qué clases pertenecen a cual.
2. La responsabilidad de cada clase creada.

Responda y escriba dentro del Readme con las siguientes preguntas:

1. En qué consiste el principio de responsabilidad única? Cuál es su propósito?
2. Qué características tiene, según su opinión, un “buen” código o código limpio

Entregables:

1. Archivo APK.
2. Link de github del código de la prueba
3. Dentro de repositorio de github el readme con las explicaciones y preguntas solicitadas.


Deadline:
Tres días o 72 horas después de la confirmación de recibido del email.  

```

### Application Layers

- movies
  - MoviesFragment = Movies List Fragment View
  - MoviesViewModel = Movies View Model, the persistence connection from the MoviesFragment to the data
  - MoviesRepository = All the data operations required by the MoviesFragment and MoviesViewModel
  - MoviesListAdapter = The RecyclerAdapter for the Grid of Movies displayed on MoviesFragment
    
- details
  - DetailsFragment = Movie Details Fragment View
  - DetailsViewModel = Movie Details connection to the data required, (videos and genres)
  - DetailsViewModelFactory = Create the ViewModel with some additional constructor params required
  - DetailsRepository = All the data operations required by the Fragment and ViewModel
  
- data
  - database
    - AppDatabase = RoomDatabase creation with a genreDao and a movieDao
    - Converters = Room Field Conversion (ListInt to String and vice versa)
    - GenreDao = Data operations with the respective table Genre (Insert, Delete...)
    - MovieDao = Data operations with the respective table Movie (Insert, Delete...)
  - models
    - Genre = Genre Model that will be used for json parsing response and as a table with room
    - Movie = Movie Model that will be used for json parsing response and as a table with room
    - Video = Video Model that will be used for json parsing response and as a table with room
  - network
    - TmdbApi = All the requests to the api https://api.themoviedb.org/3/

- ui
  - BindingAdapters = A new ImageView attr called posterPath, creating the image from the url string
  - MoviesGlideModule = @GlideModule class required by Glide

### Single Responsibility Principle
The SRP is basically a principle that says that each class, function, or module, must take care only one and only one thing of the application

### Good and clean code, from my opinion
A good and clean code, from my perspective, should:
- Be organized -> Well spared in modules, packages and classes
- Be informative -> Names must be informative, you should read it and understand what it does
- Be clean -> Well indented and spaced
- Be understandable -> Even if a 1 line of code can replace 5, that should not be done if nobody will understand
- Have a good Design Pattern -> Actually MVVM is the top architecture for various reasons
- Contain updated libraries -> Use only libraries maintained by the creators
- Make use of the language features
- Be well tested -> Test what should be tested (not everything should be)
