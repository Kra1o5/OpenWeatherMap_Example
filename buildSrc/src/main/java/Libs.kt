/**
 * Define App Libs
 */
object Libs {
    val androidLibs = listOf(
        "androidx.core:core-ktx:${Versions.core}",
        "androidx.compose.ui:ui:${Versions.compose}",
        "androidx.compose.material:material:${Versions.compose}",
        "androidx.compose.ui:ui-tooling-preview:${Versions.compose}",
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifeCycle}",
        "androidx.activity:activity-compose:${Versions.activity}",
        "com.google.maps.android:maps-compose:1.2.0",
        "androidx.lifecycle:lifecycle-viewmodel-compose:2.4.1",
        "androidx.constraintlayout:constraintlayout-compose:1.0.0",
        "androidx.appcompat:appcompat:${Versions.appCompat}",
        "com.google.android.material:material:${Versions.material}",
        "androidx.constraintlayout:constraintlayout:${Versions.constraint}",
        "com.google.dagger:hilt-android:${Versions.hilt}",
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifeCycle}",
        "androidx.activity:activity-ktx:${Versions.activity}",
        "com.google.android.gms:play-services-maps:${Versions.maps}",
        "com.google.android.gms:play-services-location:${Versions.location}"
    )

    val androidKaptLibs = listOf(
        "com.google.dagger:hilt-compiler:${Versions.hilt}"
    )

    val kotlinLibs = listOf(
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    )

    val testLibs = listOf(
        "junit:junit:${Versions.junit}"
    )

    val androidTestLibs = listOf(
        "androidx.test.espresso:espresso-core:${Versions.espresso}",
        "androidx.test.ext:junit:${Versions.junitKtx}",
        "androidx.compose.ui:ui-test-junit4:${Versions.compose}",
        "androidx.test.ext:junit-ktx:${Versions.junitKtx}",
    )

    val androidDebugLibs = listOf(
        "androidx.compose.ui:ui-tooling:${Versions.compose}"
    )
}