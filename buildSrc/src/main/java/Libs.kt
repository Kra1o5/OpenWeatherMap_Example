/**
 * Define App Libs
 */
object Libs {
    val androidLibs = listOf(
        "androidx.core:core-ktx:${Versions.core}",
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
        "androidx.test.ext:junit-ktx:${Versions.junitKtx}",
    )
}