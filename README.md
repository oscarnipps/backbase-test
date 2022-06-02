# Architecture
This app uses MVVM (Model View View-Model) architecture with a single activity multiple fragments setup (using android navigation components for navigation) and a repository pattern to access data from the network source

## Built With
* Kotlin
* RxJava 
* Navigation Components
* DataBinding
* Glide (loading images from url)
* ViewPager2
* Paging3 (paging data from the network)
* Retrofit
* Gson
* Timber (logging and debugging)
* ViewModel
* Junit4 (unit tests)
* Espresso (automated UI tests)
* MockWebServer (testing API service interactions)
* Dagger Hilt (dependency injection)


## 3rd Party Libraries Used
* RxJava : Used for all concurrency operations
* Navigation Component : Handle for navigation between the fragments and a easier way to pass arguments using safe args
* DataBinding : Used to bind UI components in layouts to data sources in your app using a declarative format rather than programmatically
* Glide : For Handling loading the movie images
* Timber : Logging and debugging purposes
* MockWebServer : Used to mock the behavior of an actual remote server but doesn’t make calls over the internet. This makes it easy to test different scenarios without internet access. Basically testing how your code reacts with server responses
* Dagger Hilt : For dependencies injection, providing dependencies for the various modules
* Paging3 : For loading and displaying pages of data from a larger dataset from network

## Development Approach
* Implementation Approach : Decided to utilize mvvm architecture in developing the movie app with a repository which basically is responsible for getting the data from a source (the network in this case). The flow of data goes from the repository in form of an rxjava observable type which is then sent to the viewmodel. The viewmodel then subscribes to the data with methods for handling the success or error case scenarios by wrapping them in a resource class that exposes the result data with varying states (i.e ERROR, LOADING, SUCCESS). The UI then observes the changes and reacts to the data according to the state (i.e if it's loading show a progress bar , if there is an error show the erro view e.t.c)

* Rating View : With a minimal understanding of custom views (would definetly pick it up after this) and the time constraint, the decision was to use custom background drawables for the rating (low rating for below 50 and high rating for above 50) which was then used to set the start drawable of a textview by using binding adapters (i.e creating a binding adapter that takes in the rating value and sets the appropriate start drawable on the textview ) 


## Addded Extra Requirements
Added cache mechanism using okhttp interceptor. This caches requests made to the movie details and most popular movies endpoint for a time range of 10 minutes (i.e api calls made to those endpoint within the time limit AKA 'maxAge' gets returned from the cache). This was achieved by creating a MovieCahceInterceptor and adding it via the okHttp builder. Reason behind this was to reduce the number of api call hits.

## Strange Behaviours
The api response received when the now playing endpoint is received does not have the 'runtime' field which was specified in the UI mockup for the movie item. However there was a decision to alter the UI for the playing now movie item.


