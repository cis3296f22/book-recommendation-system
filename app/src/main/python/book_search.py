#!/usr/bin/env python
# coding: utf-8

# In[4]:


# Create search engine!
# Goodreads json file is too large to open entirely using a pandas Dataframe. Instead, we first read it line by line:
import csv
import pandas as pd


# In[5]:


book_titles = pd.read_csv('book_fields.csv')


# In[6]:


def modify_book_titles(book_titles):
    book_titles["ratings"] = pd.to_numeric(book_titles["ratings"])
    book_titles["modified_title"] = book_titles["title"].str.replace("[^a-zA-Z0-9 ]", "", regex=True) #this is a regular expression that modifies titles so that any that include characters other than 
    book_titles["modified_title"] = book_titles["modified_title"].str.lower()                                                                                     #those in the brackets are removed.
    book_titles["modified_title"] = book_titles["modified_title"].str.replace("\s+", " ", regex=True) # replace multiple spaces with one space
    book_titles = book_titles[book_titles["modified_title"].str.len() > 0] # removing blank titles
    return book_titles


# In[7]:


book_titles = modify_book_titles(book_titles)


# In[8]:


def clickable(val):
    return '<a target="_blank" href={}"> Goodreads </a>'.format(val)
    
# ^^ This function would allow you to click on the link to see it in Goodreads. Feel like we might not want our app to redirect to another book site


# To create the search engine, we're using TF-IDF (term frequency - inverse document frequency). It uses both of these to assign keyword scores and estimate the importance/relevance of each word 
# put into the search engine.

# term frequency measures the frequency of each unique word.
# inverse document frequency minimizes the importance of common words (like the, and, etc.)

import numpy as np
import re
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
vectorizer = TfidfVectorizer()
tfidf = vectorizer.fit_transform(book_titles["modified_title"])

# show cover image in search
def cover(val):
    return '<img src="{}" width=60></image>'.format(val)

# search for a specific book (by title)
def search(query, vectorizer):
    processed = re.sub("[^a-zA-Z0-9 ]", "", query.lower())
    query_vec = vectorizer.transform([processed])
    similarity = cosine_similarity(query_vec, tfidf).flatten()
    indices = np.argpartition(similarity, -10)[-10:] #find indices of 10 largest similarity values
    results = book_titles.iloc[indices]
    results = results.sort_values("ratings", ascending=False)
    return results.head(5)#.style.format({'cover':cover, 'url': clickable})


# In[9]:


liked_books = []
book_ids = []
liked_title = []
ratings = []
def user_search(liked_books, book_ids, liked_title):
    book = input("Please enter a book to search. Enter 'exit' when finished.")
    if book == 'exit':
        return False
    results = search(book, vectorizer)
    display(results.style.format({'cover':cover, 'url': clickable}))
   # results['book_id']
    validate = input("Which result is the book you want to add to your list? Enter a number between 1 and 5. Enter N if it is not on the list. \n")
    if validate == 'N':
        print("Try again.")
    else:
        rating = input("What rating would you give this book between 1 and 5?")
        rate = int(rating)
        validate = int(validate)
        ratings.append(rate)
        book_ids.append(results.iloc[validate-1, 0])
        liked_books.append(results.iloc[validate-1, 0])
        liked_title.append(results.iloc[validate-1, 1])
    return True, liked_books, liked_title, ratings

def search_loop():
    uid = input("What is your user id?")
    user_id = int(uid)
    userin = input("\n\n\nWould you like to search for a book? Enter Y/N.")
    if userin != 'N' and userin != 'Y' and userin != 'n' and userin != 'y':
        print("Please enter Y or N.")
        userin = input("\n\n\nWould you like to search for a book? Enter Y/N.")
    while True:
        if userin == 'N':
            print("Goodbye!")
            break
        if not user_search(liked_books, book_ids, liked_title):
            break
        userin = input("Would you like to search for another book? Enter Y/N.")
    return user_id
        


# In[12]:


user_id = search_loop()
liked_book = pd.DataFrame(columns= ['book_id', 'title'])
ids = []
for i in range(0, len(book_ids)):
    ids.append(user_id)
#search_loop()
liked_book['user_id'] = ids
liked_book['book_id'] = book_ids
liked_book['title'] = liked_title
liked_book['rating'] = ratings


# In[ ]:


display(liked_books)
def return_liked(liked_books):
    return liked_books


# In[20]:


liked_book.to_csv("liked_books.csv")

