#!/usr/bin/env python
# coding: utf-8

# In[115]:


# still need to convert the liked_books csv into a list so its not hard coded. & create the really long liked books csv to do this--if we're lazy we could just grab the one from the open source repo
import csv
import pandas as pd

def generate_liked_books():
    book_info = pd.read_csv("liked_books.csv")
    liked_books = book_info['book_id'].tolist()
    for i in range(0, len(liked_books)):
        liked_books[i] = str(liked_books[i])
    return liked_books
#liked_books = ['12961964', '84979', '29983711', '29044', '17333794', '18143977', '2865', '6457081','12837725']


# In[116]:


def create_book_mapping():
    book_mapping = {}
    with open("book_id_map.csv", "r") as f:
        while True:
            line = f.readline()
            if not line:
                break
            csv_id, book_id = line.strip().split(",")
            book_mapping[csv_id] = book_id
    return book_mapping


# In[117]:


def get_overlap_users():
    overlap_users = set()
    count = 0
    # overlap_users contains a list of users who liked the books
    with open("goodreads_interactions.csv", "r") as f:
        while True:
            if count > 10000000:
                break
            line = f.readline()
            if not line:
                break
            user_id, csv_id, _, rating, _ = line.split(",")

            if user_id in overlap_users:
                continue
            try:
                rating = int(rating)
            except ValueError:
                continue

            book_id = book_mapping[csv_id]
            if book_id in liked_books and rating >=4:
                overlap_users.add(user_id)
            count+=1
    return overlap_users


# In[118]:


def create_recs(overlap_users, book_mapping):
    recs = []
    count = 0
    # recs contains books that users who liked similar books have read.
    with open("goodreads_interactions.csv", "r") as f:
        while True:
            if count > 10000000:
                break
            line = f.readline()
            if not line:
                break
            user_id, csv_id, _, rating, _ = line.split(",")

            if user_id in overlap_users:
                book_id = book_mapping[csv_id]
                recs.append([user_id, book_id, rating])            
            count+=1
        
    return recs


# In[119]:


def update_recs(recs):
    rec = pd.DataFrame(recs, columns = ['user_id', 'book_id', 'rating'])
    rec['book_id'] = rec['book_id'].astype(str)
    top_recs = rec['book_id'].value_counts().head(10) #counts up number of times each book occurred. shows top books
    top_recs = top_recs.index.values
    book_titles = pd.read_json("book_titles.json")
    book_titles["book_id"] = book_titles["book_id"].astype(str)
    book_titles[book_titles["book_id"].isin(top_recs)]
    all_recs = rec["book_id"].value_counts()
    all_recs = all_recs.to_frame().reset_index()
    all_recs.columns = ["book_id", "book_count"]
    all_recs = all_recs.merge(book_titles, how="inner", on="book_id")
    all_recs["score"] = all_recs["book_count"] * (all_recs["book_count"] / all_recs["ratings"] * 2) #penalizes popularity so that the recs arent always books with the most ratings
    all_recs.sort_values("score", ascending=False).head(10)
    return all_recs


# In[123]:


def clickable(val):
    return '<a target="_blank" href={}"> Goodreads </a>'.format(val)

def cover(val):
    return '<img src="{}" width=60></image>'.format(val)

def generate_recs():
    liked_books = generate_liked_books()
    book_mapping = create_book_mapping()
    overlap_users = get_overlap_users()
    recs = create_recs(overlap_users, book_mapping)
    all_recs = update_recs(recs)
    popular_recs = all_recs[all_recs["book_count"] > 75].sort_values("score", ascending=False)
    best_recs = popular_recs[~popular_recs["book_id"].isin(liked_books)].head(10)
    return best_recs


# In[124]:


best_recs = generate_recs()
display(best_recs.style.format({'cover':cover, 'url': clickable}))


# In[ ]:




