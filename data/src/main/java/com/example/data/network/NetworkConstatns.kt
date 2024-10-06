package com.example.data.network

internal const val BASEURL = "https://api.github.com/"

internal const val PATH_USERNAME = "username"
internal const val PATH_DOWNLOAD_OWNER = "owner"
internal const val PATH_DOWNLOAD_REPO = "repo"
internal const val HEADER_AUTH = "Authorization"

internal const val ENDPOINT_USER = "user"
internal const val ENDPOINT_GET_USER_REPOS = "users/{${PATH_USERNAME}}/repos"
internal const val ENDPOINT_DOWNLOAD_REPO = "repos/{$PATH_DOWNLOAD_OWNER}/{$PATH_DOWNLOAD_REPO}/zipball/"
