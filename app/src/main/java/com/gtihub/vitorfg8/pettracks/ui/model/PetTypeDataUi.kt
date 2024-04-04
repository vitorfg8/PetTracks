package com.gtihub.vitorfg8.pettracks.ui.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.gtihub.vitorfg8.pettracks.R

enum class PetTypeDataUi(@DrawableRes val drawableRes: Int, @StringRes val localizedName: Int) {
    Bird(R.drawable.bird_solid, R.string.bird),
    Cat(R.drawable.cat_solid, R.string.cat),
    Dog(R.drawable.dog_solid, R.string.dog),
    Fish(R.drawable.fish_solid, R.string.fish),
    Reptile(R.drawable.reptile_solid, R.string.reptile),
    Other(R.drawable.paw_solid, R.string.other)
}
