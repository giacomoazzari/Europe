package esp.project.europe

enum class Origin {
    LIST, MAP
}

enum class State {
    TABLETOP, BOOK, TABLET
}

interface OnNavigationButtonsListener {
    fun onCountrySelected(country: Country?, provenience: Origin)
    fun onDiscoverButtonClick(provenience: State)
}