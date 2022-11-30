package edu.temple.bookrecommendationsystem

/* data classes in kotlin have appropriate getters/setters for defined fields
   this class will be used to encapsulate, transfer, and store the relevant book info for the app
 */
data class Book(val title: String, val coverURL: String)
//TODO: convert coverURL from Int to String when we use real data. needs to be updated here and in all usages
