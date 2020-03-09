package fr.granjon.template.ui.utils


enum class UserSearchFilter(val frenchFilter: String, val apiFilter: String) {
    FOLLOWERS(FrenchFilter.FOLLOWERS_FILTER, APIFilter.FOLLOWERS_FILTER),
    REPOS(FrenchFilter.REPOS_FILTER, APIFilter.REPOS_FILTER),
    DATE(FrenchFilter.DATE_FILTER, APIFilter.DATE_FILTER)
}

private object APIFilter{
    const val FOLLOWERS_FILTER = "followers"
    const val REPOS_FILTER = "repositories"
    const val DATE_FILTER = "joined"
}

private object FrenchFilter{
    const val FOLLOWERS_FILTER = "Followers"
    const val REPOS_FILTER = "Repos"
    const val DATE_FILTER = "Date de création"
}
