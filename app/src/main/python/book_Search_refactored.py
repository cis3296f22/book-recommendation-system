import csv
import pandas as pd

book_titles = []

def driver():
    x = "b_s_refactored"    
    return x
    
def main(query):
    global book_titles
    filename = join(dirname(__file__), "book_fields.csv")
    book_titles = pd.read_csv(filename)
    book_titles = modify_book_titles(book_titles)
    x = search(query)
    return x

def modify_book_titles(book_titles):
    book_titles["ratings"] = pd.to_numeric(book_titles["ratings"])
    book_titles["modified_title"] = book_titles["title"].str.replace("[^a-zA-Z0-9 ]", "", regex=True) # removes titles that contain anything other than a-z and numbers 
    book_titles["modified_title"] = book_titles["modified_title"].str.lower() # putting titles to all lowercase                                                                               
    book_titles["modified_title"] = book_titles["modified_title"].str.replace("\s+", " ", regex=True) # replace multiple spaces with one space
    book_titles = book_titles[book_titles["modified_title"].str.len() > 0] # removing blank titles
    return book_titles

# inverse document frequency minimizes the importance of common words (like the, and, etc.)
import numpy as np
import re
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
# above is all preliminary to the actual search. it's just preparing the data in the book_fields csv. will probably
# refactor it out later.

# searching for a book by title:
def search(query):
    global book_titles
    vectorizer = TfidfVectorizer()
    tfidf = vectorizer.fit_transform(book_titles["modified_title"])
    processed = re.sub("[^a-zA-Z0-9 ]", "", query.lower())
    query_vec = vectorizer.transform([processed])
    similarity = cosine_similarity(query_vec, tfidf).flatten()
    indices = np.argpartition(similarity, -10)[-10:] #find indices of 10 largest similarity values
    results = book_titles.iloc[indices]
    results = results.sort_values("ratings", ascending=False)

    results_csv = results.head(5).to_csv("results.csv")
    return results_csv

