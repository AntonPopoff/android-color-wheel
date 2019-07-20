# ColorWheel

**ColorWheel** is a library for Android that provides **HSV Color Wheel**
and **Linear Gradient Seek Bar** views that can be used to pick an **ARGB** color.

## Requirements

**The minimal required Android API version is 19 (Android 4.4).**

## Screenshots

<img src="screenshots/preview_gif_00.gif" width="270" height="480"> <img src="screenshots/screenshot_00.png" width="270" height="480"> <img src="screenshots/screenshot_01.png" width="270" height="480">

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

To start using the views just add `ColorWheel` or `GradientSeekBar` to your xml layout file:

```xml
<com.apandroid.colorwheel.ColorWheel
    android:id="@+id/colorWheel"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />

<com.apandroid.colorwheel.gradientseekbar.GradientSeekBar
    android:id="@+id/gradientSeekBar"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

## Basic Usage

### ColorWheel

To **set** or **get** an RGB color you can use `ColorWheel.rgb` property:

```kotlin
val colorWheel = findViewById<ColorWheel>(R.id.colorWheel)

colorWheel.rgb = Color.rgb(13, 37, 42)

val currentColor = colorWheel.rgb
```

**Note**: when you **set** an RGB or ARGB color it is transformed to the closest 
**HSV** color with **value** component set to 1 so the color can be correctly displayed 
on the color wheel. Therefore currently selected color on `ColorWheel` may differ from 
the original color you have set. To allow a user to change brightness or alpha component
of a color picked from `ColorWheel` you can use `GradientSeekBar`.

Also you can set `colorChangeListener` that will be called every time when currently
selected color is changed:

```kotlin
colorWheel.colorChangeListener = { rgb: Int ->
    // Listener Code
}
```
### GradientSeekBar

`GradientSeekBar` draws a **vertical** or **horizontal** bar filled with linear
gradient of two colors. You can use it to pick an intermediate color between
those two.

#### Color

To pick an intermediate **ARGB** color you can use `GradientSeekBar.currentColor` 
property:

```kotlin
val color = gradientSeekBar.currentColor
```

To **set** or **get** `start` or `end` colors to or from `GradientSeekBar` you can 
use the following properties and methods:

```kotlin
val startColor = Color.argb(0, 0, 0, 0)
val endColor = Color.argb(0xff, 0xff, 0xff, 0xff)

gradientSeekBar.startColor = startColor
gradientSeekBar.endColor = endColor
gradientSeekBar.setColors(startColor, endColor)
```

Also you can set a `listener` that will be called every time an intermediate color is 
changed:

```kotlin
gradientSeekBar.currentColor = { offset: Float, argb: Int ->
    // Listener code
}
```

If you want to change an intermediate color programmatically you can use
`GradientSeekBar.offset` property. This value is always within
range from `0f` to `1f` and shows how close an intermediate color to
a start or an end color.
