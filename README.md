# ColorWheel

**ColorWheel** is a library for Android which contains helpful views
which can be used to pick an **ARGB** color.

## Screenshots

<img src="screenshots/preview_gif_00.gif" width="270" height="480"> <img src="screenshots/screenshot_00.png" width="270" height="480"> <img src="screenshots/screenshot_01.png" width="270" height="480">

## Gradle

**From version `1.1.13` the library will be hosted on Maven Central Repository because JCenter
announced its deprecation in the future. This migration lead to change of the group id
which is now `com.github.antonpopoff`.**

**The minimal required Android version is 16 (Android 4.1).**

```groovy
// For version 1.1.13 and above
implementation 'com.github.antonpopoff:colorwheel:1.1.13'

// For version 1.1.12 and below
implementation 'com.apandroid:colorwheel:1.1.12'
```

In case of any problems make sure that **jCenter** or **Maven Central** repositories are specified
in your `build.gradle` file:

```groovy
repositories {
    mavenCentral() // Since version 1.1.13.
    jcenter() // For version 1.1.12 and below.
}
```

## Getting Started


**For version `1.1.13` and above:**

```xml
<com.github.antonpopoff.ColorWheel
    android:id="@+id/colorWheel"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />

<com.github.antonpopoff.gradientseekbar.GradientSeekBar
    android:id="@+id/gradientSeekBar"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

**For version `1.1.12` and below:**

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

To `set/get` a RGB color you can use `ColorWheel.rgb` property:

```kotlin
val colorWheel = findViewById<ColorWheel>(R.id.colorWheel)

val previousColor = colorWheel.rgb

colorWheel.rgb = Color.rgb(13, 37, 42)
```

You can set a listener via `ColorWheel.colorChangeListener` property:

```kotlin
colorWheel.colorChangeListener = { rgb: Int -> /* Code */ }
```

**Important note: keep in mind that `ColorWheel` is a two-dimensional implementation of HSV color
model. Two-dimensional in that case means that its brightness (value) component of HSV is always
set to `1`. Because of that, `ColorWheel` can't display every single color supported by HSV.**

**Therefore if you set a color which can't be displayed it will be transformed to the
closest *HSV* color which can be displayed by `ColorWheel`. In order to pick colors
which can't be displayed by `ColorWheel` you can additionally use `GradientSeekBar`.**

### GradientSeekBar

`GradientSeekBar` view draws a vertical or horizontal bar filled with a linear gradient
of two colors. You can use the view to pick an intermediate color in-between.

#### Color

To pick an intermediate ARGB color you can use `GradientSeekBar.argb` property:

```kotlin
val color = gradientSeekBar.argb
```

To `set/get` `start/end` colors you can use the following properties and methods:

```kotlin
val startColor = Color.argb(0, 0, 0, 0)
val endColor = Color.argb(0xff, 0xff, 0xff, 0xff)

gradientSeekBar.startColor = startColor
gradientSeekBar.endColor = endColor
gradientSeekBar.setColors(startColor, endColor)
```

If you want to change an intermediate color programmatically you can use `GradientSeekBar.offset`
property. Its value lies within the range from `0f` to `1f` and reflects how close
the intermediate color to `startColor` or `endColor` properties.

You can set a listener via `GradientSeekBar.colorChangeListener` property:

```kotlin
gradientSeekBar.colorChangeListener = { offset: Float, argb: Int -> /* Code */ }
```

#### Brightness

It's impossible to pick all different colors from `ColorWheel` because it's only two-dimensional
and the **brightness (value)** component of **HSV** is always set to `1`.

To compensate that you can use `GradientSeekBar` and its `setBlackToColor(color)` extension.
This method sets black color as `startColor` and supplied color as `endColor`. This gives you a
possibility to pick additional shades of a color picked from `ColorWheel`.

#### Alpha

If you want to use `GradientSeekBar` to pick the alpha value of a color in range from `0` to `255`
you can use the following extension:

`GradientSeekBar.setTransparentToColor(color: Int, respectAlpha: Boolean = true)`

This method takes an ARGB color and sets the transparent version of it as `startColor` and
an opaque version as `endColor`. `respectAlpha` parameter determines whether or not the thumb's
position will be adjusted based on the alpha value of the color you supplied.

To get the alpha value of the current color you can use `GradientSeekBar.currentColorAlpha`
extension property.

You can use `GradientSeekBar.setAlphaChangeListener` extenstion to set a listener that has additional
`alpha` parameter which lies withing the range from `0` to `255`.

```kotlin
gradientSeekBar.setAlphaChangeListener { offset: Float, color: Int, alpha: Int -> /* Code */ }
```

### Additional Customizing

You can use the following XML attributes to additionally customize `ColorWheel` and `GradientSeekBar`.

#### ColorWheel

| XML Attribute              | Property                | Description                                                                                                                                                                          |
|----------------------------|-------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `tb_thumbRadius`           | `thumbRadius`           | Sets `ColorWheel`'s thumb radius                                                                                                                                                     |
| `tb_thumbColor`            | `thumbColor`            | Sets `ColorWheel`'s thumb color                                                                                                                                                      |
| `tb_thumbStrokeColor`      | `thumbStrokeColor`      | Sets `ColorWheel`'s thumb stroke color                                                                                                                                               |
| `tb_thumbColorCirlceScale` | `thumbColorCircleScale` | Sets `ColorWheel`'s thumb color circle size which is relative to `thumbRadius`. This value is in range from `0` to `1`. If it set to `1` it will have the same size as `thumbRadius` |

#### GradientSeekBar

**For version `1.1.13` and above:**

| XML Attribute               | Property                | Description                                                                                                                                                                               |
|-----------------------------|-------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `tb_thumbRadius`            | `thumbRadius`           | Sets `GradientSeekBar`'s thumb radius                                                                                                                                                     |
| `tb_thumbColor`             | `thumbColor`            | Sets `GradientSeekBar`'s thumb color                                                                                                                                                      |
| `tb_thumbStrokeColor`       | `thumbStrokeColor`      | Sets `GradientSeekBar`'s thumb stroke color                                                                                                                                               |
| `tb_thumbColorCirlceScale`  | `thumbColorCircleScale` | Sets `GradientSeekBar`'s thumb color circle size which is relative to `thumbRadius`. This value is in range from `0` to `1`. If it set to `1` it will have the same size as `thumbRadius` |
| `gsb_barSize`               | `barSize`               | Sets width(vertical)/height(horizontal) of `GradientSeekBar`'s gradient bar depending on it's orientation                                                                             |
| `gsb_barCornersRadius`      | `cornerRadius`          | Sets `GradientSeekBar`'s gradient bar corners radius                                                                                                                                      |
| `gsb_startColor`            | `startColor`            | Sets `GradientSeekBar`'s startColor                                                                                                                                                       |
| `gsb_endColor`              | `endColor`              | Sets `GradientSeekBar`'s endColor                                                                                                                                                         |
| `gsb_offset`                | `offset`                | Sets `GradientSeekBar`'s offset                                                                                                                                                           |
| `gsb_orientation`           | `orientation`           | Sets `GradientSeekBar`'s orientation. Possible values: `vertical`/`horizontal`                                                                                                            |

**For version `1.1.12` and below:**

| XML Attribute               | Property                | Description                                                                                                                                                                               |
|-----------------------------|-------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `tb_thumbRadius`            | `thumbRadius`           | Sets `GradientSeekBar`'s thumb radius                                                                                                                                                     |
| `tb_thumbColor`             | `thumbColor`            | Sets `GradientSeekBar`'s thumb color                                                                                                                                                      |
| `tb_thumbStrokeColor`       | `thumbStrokeColor`      | Sets `GradientSeekBar`'s thumb stroke color                                                                                                                                               |
| `tb_thumbColorCirlceScale`  | `thumbColorCircleScale` | Sets `GradientSeekBar`'s thumb color circle size which is relative to `thumbRadius`. This value is in range from `0` to `1`. If it set to `1` it will have the same size as `thumbRadius` |
| `asb_barSize`               | `barSize`               | Sets width(vertical)/height(horizontal) of `GradientSeekBar`'s gradient bar depending on it's orientation                                                                             |
| `asb_barCornersRadius`      | `cornerRadius`          | Sets `GradientSeekBar`'s gradient bar corners radius                                                                                                                                      |
| `asb_startColor`            | `startColor`            | Sets `GradientSeekBar`'s startColor                                                                                                                                                       |
| `asb_endColor`              | `endColor`              | Sets `GradientSeekBar`'s endColor                                                                                                                                                         |
| `asb_offset`                | `offset`                | Sets `GradientSeekBar`'s offset                                                                                                                                                           |
| `asb_orientation`           | `orientation`           | Sets `GradientSeekBar`'s orientation. Possible values: `vertical`/`horizontal`                                                                                                            |

## License

MIT
