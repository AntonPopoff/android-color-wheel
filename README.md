# ColorWheel

**ColorWheel** is a library for Android that provides **HSV Color Wheel**
and **Linear Gradient Seek Bar** views that can be used to pick an **ARGB** color.

## Requirements

**The minimal required Android API version is 19 (Android 4.4).**

## Screenshots

<img src="screenshots/preview_gif_00.gif" width="270" height="480">
<img src="screenshots/screenshot_00.png" width="270" height="480">
<img src="screenshots/screenshot_01.png" width="270" height="480">

## Installation

To add the library to your project simply add the following line to
your app module `build.gradle` file:

```groovy
implementation 'com.apandroid:colorwheel:1.0.0'
```

In case of problems make sure that **jCenter** repository is specified in
your `build.gradle` file:

```groovy
repositories {
    jcenter()
}
```

## Getting Started

To start using the views just add `ColorWheel` or `GradientSeekBar` to your xml layout file.

```xml
<com.colorwheelapp.colorwheel.ColorWheel
    android:id="@+id/colorWheel"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />

<com.colorwheelapp.colorwheel.gradientseekbar.GradientSeekBar
    android:id="@+id/gradientSeekBar"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

## Basic Usage

To **get** or **set** color from `ColorWheel` you can use `ColorWheel.rgb` property. This property returns you current RGB color that is an
equivavelent of a HSV color that is selected on ColorWheel.

```kotlin
val colorWheel = findViewById<ColorWheel>(R.id.colorWheel)
colorWheel.rgb = Color.rgb(13, 37, 42)
```

Also you can register a listener that will notify you when selected color is changed either by a gesture or by the setter.

```kotlin

```
