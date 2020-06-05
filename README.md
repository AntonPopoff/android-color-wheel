# ColorWheel

**ColorWheel** is a library for Android which contains helpful views which can be used to pick an **ARGB** color.

## Requirements

**The minimal required Android API version is 16 (Android 4.1).**

## Screenshots

<img src="screenshots/preview_gif_00.gif" width="270" height="480"> <img src="screenshots/screenshot_00.png" width="270" height="480"> <img src="screenshots/screenshot_01.png" width="270" height="480">

## Installation

To add the library to your project simply add the following line to your app module `build.gradle` file:

```groovy
implementation 'com.apandroid:colorwheel:1.1.11'
```

In case of problems make sure that **jCenter** repository is specified in your `build.gradle` file:

```groovy
repositories {
    jcenter()
}
```

## Getting Started

To start using views just add `ColorWheel` or `GradientSeekBar` to your xml layout file:

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

To set or get a RGB color you can use `ColorWheel.rgb` property:

```kotlin
val colorWheel = findViewById<ColorWheel>(R.id.colorWheel)

colorWheel.rgb = Color.rgb(13, 37, 42)

val currentColor = colorWheel.rgb
```

**Note**: when you set an ARGB color to `ColorWheel` it's transformed to the closest HSV color with the value(brightness) 
component of it set to 1 so the color can be correctly displayed on `ColorWheel` since it's only 2 dimensional. 
Therefore the color that is displayed by `ColorWheel` may differ from the original color which you have set. To change the brightness 
or the alpha component of a color picked from `ColorWheel` you can use `GradientSeekBar` view.

To be notified when the current color on `ColorWheel` is changed you can use `ColorWheel.colorChangeListener` property:

```kotlin
colorWheel.colorChangeListener = { rgb: Int ->
    // Listener Code
}
```

### GradientSeekBar

`GradientSeekBar` view draws a vertical or horizontal bar filled with a linear gradient of two colors.
You can use it to pick an intermediate color between these two.

#### Color

To pick the intermediate ARGB color you can use `GradientSeekBar.argb` property:

```kotlin
val color = gradientSeekBar.argb
```

To set/get `start` and `end` colors of `GradientSeekBar` you can use the following properties and methods:

```kotlin
val startColor = Color.argb(0, 0, 0, 0)
val endColor = Color.argb(0xff, 0xff, 0xff, 0xff)

gradientSeekBar.startColor = startColor
gradientSeekBar.endColor = endColor
gradientSeekBar.setColors(startColor, endColor)
```

You can set a color listener via `GradientSeekBar.colorListener`:

```kotlin
gradientSeekBar.colorChangeListener = { offset: Float, argb: Int ->
    // Listener code
}
```

If you want to change the intermediate color programmatically you can use `GradientSeekBar.offset` property. This value is always within
the range from `0f` to `1f` and shows how close the intermediate color to the start color or end color.

#### Alpha

If you want to use `GradientSeekBar` to pick the alpha value of a color in range from `0` to `255`
you can use the following extension of `GradientSeekBar`:

* `GradientSeekBar.setTransparentToColor(color: Int, respectAlpha: Boolean = true)`

This method takes an ARGB color and sets the transparent version of it as the `start` color and an opaque one as the `end` color,
`respectAlpha` parameter determines if the thumb's position will be adjusted based on the alpha value of the color you supplied.

To get the alpha value of the current color you can use `GradientSeekBar.currentColorAlpha` extension property.

Also you can use `GradientSeekBar.setAlphaChangeListener` method to set a listener that will be called when
the alpha value of a color is changed.

```kotlin
gradientSeekBar.setAlphaChangeListener { offset: Float, color: Int, alpha: Int ->
    // Listener code
}
```

#### Brightness

It's impossible to pick all different colors from `ColorWheel` since it's only 2 dimensional and the value(brightness) component of it
is always set to `1`.

To compensate that you can use `GradientSeekBar` and its `setBlackToColor(color)` extension. This method sets black color as
the `start` color and provided color as the `end` color. This gives you a possibility to pick additional shades of a color selected from `ColorWheel`.

### Additional Customizing

You can use the following XML attributes to additionally customize `ColorWheel` and `GradientSeekBar`.

#### ColorWheel

| XML Attribute              | Property                | Description                                                                                                                                                                          |
|----------------------------|-------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `cw_thumbRadius`           | `thumbRadius`           | Sets `ColorWheel`'s thumb radius                                                                                                                                                     |
| `cw_thumbColor`            | `thumbColor`            | Sets `ColorWheel`'s thumb color                                                                                                                                                      |
| `cw_thumbStrokeColor`      | `thumbStrokeColor`      | Sets `ColorWheel`'s thumb stroke color                                                                                                                                               |
| `cw_thumbColorCirlceScale` | `thumbColorCircleScale` | Sets `ColorWheel`'s thumb color circle size which is relative to `thumbRadius`. This value is in range from `0` to `1`. If it set to `1` it will have the same size as `thumbRadius` |

#### GradientSeekBar

| XML Attribute               | Property                | Description                                                                                                                                                                               |
|-----------------------------|-------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `asb_thumbRadius`           | `thumbRadius`           | Sets `GradientSeekBar`'s thumb radius                                                                                                                                                     |
| `asb_thumbColor`            | `thumbColor`            | Sets `GradientSeekBar`'s thumb color                                                                                                                                                      |
| `asb_thumbStrokeColor`      | `thumbStrokeColor`      | Sets `GradientSeekBar`'s thumb stroke color                                                                                                                                               |
| `asb_thumbColorCirlceScale` | `thumbColorCircleScale` | Sets `GradientSeekBar`'s thumb color circle size which is relative to `thumbRadius`. This value is in range from `0` to `1`. If it set to `1` it will have the same size as `thumbRadius` |
| `asb_barSize`               | `barSize`               | Sets width(vertical)/height(horizontal) of `GradientSeekBar`'s gradient bar depending on it's orientation                                                                             |
| `asb_barCornersRadius`      | `cornerRadius`          | Sets `GradientSeekBar`'s gradient bar corners radius                                                                                                                                      |
| `asb_startColor`            | `startColor`            | Sets `GradientSeekBar`'s startColor                                                                                                                                                       |
| `asb_endColor`              | `endColor`              | Sets `GradientSeekBar`'s endColor                                                                                                                                                         |
| `asb_offset`                | `offset`                | Sets `GradientSeekBar`'s offset                                                                                                                                                           |
| `asb_orientation`           | `orientation`           | Sets `GradientSeekBar`'s orientation. Possible values: `vertical`/`horizontal`                                                                                                            |

## License

MIT
