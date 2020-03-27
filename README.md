# ColorWheel

**ColorWheel** is a library for Android that provides **HSV Color Wheel** and **Linear Gradient Seek Bar**
UI elements that can be used to pick **ARGB** color.

## Requirements

**The minimal required Android API version is 19 (Android 4.4).**

## Screenshots

<img src="screenshots/preview_gif_00.gif" width="270" height="480"> <img src="screenshots/screenshot_00.png" width="270" height="480"> <img src="screenshots/screenshot_01.png" width="270" height="480">

## Installation

To add the library to your project simply add the following line to your app module `build.gradle` file:

```groovy
implementation 'com.apandroid:colorwheel:1.0.5'
```

In case of problems make sure that **jCenter** repository is specified in your `build.gradle` file:

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

To set or get RGB color you can use `ColorWheel.rgb` property:

```kotlin
val colorWheel = findViewById<ColorWheel>(R.id.colorWheel)

colorWheel.rgb = Color.rgb(13, 37, 42)

val currentColor = colorWheel.rgb
```

**Note**: when you set RGB or ARGB color it is transformed to the closest HSV color with value(brightness) component of it set to 1
so the color can be correctly displayed on the `ColorWheel` because it is only 2 dimensional. Therefore currently displayed color
on the `ColorWheel` may differ from the original color that you have set. To allow a user to change brightness or alpha component
of a color picked from `ColorWheel` you can use `GradientSeekBar` view.

Also you can set `colorChangeListener` that will be called every time when currently selected color is changed:

```kotlin
colorWheel.colorChangeListener = { rgb: Int ->
    // Listener Code
}
```
### GradientSeekBar

`GradientSeekBar` draws a vertical or horizontal bar filled with linear gradient of two colors.
You can use it to pick an intermediate color between these two.

#### Color

To pick intermediate ARGB color you can use `GradientSeekBar.argb` property:

```kotlin
val color = gradientSeekBar.argb
```

To set or get `start` or `end` colors to or from `GradientSeekBar` you can use the following properties and methods:

```kotlin
val startColor = Color.argb(0, 0, 0, 0)
val endColor = Color.argb(0xff, 0xff, 0xff, 0xff)

gradientSeekBar.startColor = startColor
gradientSeekBar.endColor = endColor
gradientSeekBar.setColors(startColor, endColor)
```

Also you can use `listener` property to set a listener that will be called every time an intermediate color is changed:

```kotlin
gradientSeekBar.listener = { offset: Float, argb: Int ->
    // Listener code
}
```

If you want to change an intermediate color programmatically you can use `GradientSeekBar.offset` property. This value is always within
the range from `0f` to `1f` and shows how close an intermediate color to a start or end color.

#### Alpha

If you want to use `GradientSeekBar` to pick alpha component of a color in range from `0` to `255`
you can use the following extensions of `GradientSeekBar`:

* `GradientSeekBar.setAlphaArgb(argb)`
* `GradientSeekBar.setAlphaRgb(rgb)`

Both of these methods take RGB color and set transparent version of it as `start` color and opaque as `end` color.

The only difference between these two methods is that `GradientSeekBar.setAlphaArgb(argb)` also adjusts
position of the thumb based on the alpha value of the parameter while `GradientSeekBar.setAlphaRgb(rgb)` doesn't.

To get alpha component of a current intermediate color you can use `GradientSeekBar.argbAlpha` extension property.

Also you can use `GradientSeekBar.setAlphaListener` method to set a listener that will be called when
alpha value is changed.

```kotlin
gradientSeekBar.setAlphaListener { offset: Float, color: Int, alpha: Int ->
    // Listener code
}
```

#### Brightness

It is impossible to pick all different colors from HSV Color Wheel since it is 2 dimensional and value(brightness) component of it
is always set to `1f`.

To compensate it you can use `GradientSeekBar` and it's `setBlackToColor(color)` extensions. This method sets black color as
`start` color and provided color as `end` color. This gives you a possibility to pick additional shades of a color selected from `ColorWheel`.

### Additional Customazing

You can use the following XML attributes to additionally customize `ColorWheel` and `GradientSeekBar`.

#### ColorWheel

| XML Attribute    | Property      | Description                              |
|------------------|---------------|------------------------------------------|
| `cw_thumbRadius` | `thumbRadius` | Sets the radius of `ColorWheel`'s thumb. |

#### GradientSeekBar

| XML Attribute          | Property       | Description                                                                                                          |
|------------------------|----------------|----------------------------------------------------------------------------------------------------------------------|
| `asb_thumbRadius`      | `thumbRadius`  | Sets the radius of the `GradientSeekBar`'s thumb.                                                                    |
| `asb_barSize`          | `barSize`      | Sets width(vertical)/height(horizontal) of the `GradientSeekBar`'s gradient bar depending on it's orientation.       |
| `asb_barCornersRadius` | `cornerRadius` | Sets `GradientSeekBar`'s gradient bar corners radius.                                                                |
| `asb_startColor`       | `startColor`   | Sets `GradientSeekBar`'s startColor.                                                                                 |
| `asb_endColor`         | `endColor`     | Sets `GradientSeekBar`'s endColor.                                                                                   |
| `asb_offset`           | `offset`       | Sets `GradientSeekBar`'s offset.                                                                                     |
| `asb_orientation`      | `orientation`  | Sets `GradientSeekBar`'s orientation. Possible values: `vertical`/`horizontal`.                                      |

## License

MIT
