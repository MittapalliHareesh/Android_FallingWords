# Android_FallingWords

Idea Of this Project :-

The task is to write a small language game. The player will see a word in language „one“ on the screen. While this word is displayed, a word in language „two“ will fall down on the screen. The player will have to choose if the falling word is the correct translation or a wrong translation. The player needs to answer, before the word reaches the bottom of the screen. Include a counter that gives the player feedback.

Design :- Implemented by following MMVM archtecture with RXJava and DI.  

<p align="center">
<img src="https://user-images.githubusercontent.com/16395251/90339007-75923680-e00b-11ea-83be-76790ff5a780.png" width="500" height="1000">
</p>

<p align="center">
<img src="https://user-images.githubusercontent.com/16395251/90339109-50ea8e80-e00c-11ea-9112-14fce65426cf.png" width="500" height="1000">
</p>

<p align="center">
<img src="https://user-images.githubusercontent.com/16395251/90339121-695aa900-e00c-11ea-8543-95b4e818cabb.png" width="500" height="1000">
</p>

<h3>Third party libraries which are used in this Project:- </h3>

1) Used " implementation 'com.intuit.ssp:ssp-android:1.0.6' "

An android SDK that provides a new size unit - ssp (scalable sp). This size unit scales with the screen size based on the sp size unit (for texts). It can help Android developers with supporting multiple screens.

2) Used " implementation 'com.intuit.sdp:sdp-android:1.0.4' "

An android SDK that provides a new size unit - sdp (scalable dp). This size unit scales with the screen size. It can help Android developers with supporting multiple screens.

3) Used " implementation 'com.google.android.material:material:1.2.0' "

Instead of AlertDialog used Material AlertDialog because "AlertDialog loses its content on configuration change. Hence it is recommended to use AppCompatDialogFragment. But for the sake of simplicity of this tutorial, we’ll stick with MaterialAlertDialog."

4) Used "Data Binding" for faster access and performance improvement

The Data Binding Library is a support library that allows you to bind UI components in your layouts to data sources in your app using a declarative format rather than programmatically.

5) Used RXJava and Dagger for dependancy injection libraries.

6) Used Retrofit to make API calls.
  
<h3>Explanation:-</h3>

Falling word game case study  

<h3> Assumptions:- </h3>

By using MVVM architecture sepearted View, Bussiness logic and API/Database operations. To keep observe data i have used LiveData object with RXJava. And to inject components i have used Dependency Injection Library. 

For Time Constarints could not able to write test cases and app restricted to only portrait mode.

