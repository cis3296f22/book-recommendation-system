import csv
import pandas as pd
from os.path import dirname, join

# creates a list of the liked books from the liked books csv 
def generate_liked_books():
    likedBooksFilename = join(dirname(__file__), "liked_books.csv")
    book_info = pd.read_csv(likedBooksFilename)
    liked_books = book_info['book_id'].tolist()
    for i in range(0, len(liked_books)):
        liked_books[i] = str(liked_books[i])
    return liked_books


# maps the simple csv id to the actual book id
def create_book_mapping():
    book_mapping = {}
    bookIDMapFilename = join(dirname(__file__), "book_id_map.csv")
    with open(bookIDMapFilename, "r") as f:
        while True:
            line = f.readline()
            if not line:
                break
            csv_id, book_id = line.strip().split(",")
            book_mapping[csv_id] = book_id
    return book_mapping


# finds users that have rated the books user liked over 4
def get_overlap_users(liked_books, book_mapping):
    overlap_users = set()
    # overlap_users contains a list of users who liked the books
    allInteractionsFilename = join(dirname(__file__), "all_interactions.csv")
    with open(allInteractionsFilename, "r") as f:
        while True:
            line = f.readline()
            if not line:
                break
            user_id, csv_id, _, rating, _ = line.strip().split(",")
            if user_id in overlap_users:
                continue
            try:
                rating = int(rating)
            except ValueError:
                continue
            book_id = book_mapping[csv_id]
            if book_id in liked_books and rating >=4:
                overlap_users.add(user_id)
    return overlap_users


# gets the books that users who are similar to us have rated
def create_recs(overlap_users, book_mapping):
    recs = []
    # recs contains books that users who liked similar books have read
    allInteractionsFilename = join(dirname(__file__), "all_interactions.csv")
    with open(allInteractionsFilename, "r") as f:
        while True:
            line = f.readline()
            if not line:
                break
            user_id, csv_id, _, rating, _ = line.split(",")

            if user_id in overlap_users:
                book_id = book_mapping[csv_id]
                recs.append([user_id, book_id, rating])            
    return recs


# gets the most commonly similar books between users similar to us
def update_recs(recs):
    rec = pd.DataFrame(recs, columns = ['user_id', 'book_id', 'rating'])
    rec['book_id'] = rec['book_id'].astype(str)
    top_recs = rec['book_id'].value_counts().head(10) #counts up number of times each book occurred. shows top books
    top_recs = top_recs.index.values
    bookFieldsFilename = join(dirname(__file__), "book_fields.csv")
    book_titles = pd.read_csv(bookFieldsFilename)
    book_titles["book_id"] = book_titles["book_id"].astype(str)
    book_titles[book_titles["book_id"].isin(top_recs)]
    all_recs = rec["book_id"].value_counts()
    all_recs = all_recs.to_frame().reset_index()
    all_recs.columns = ["book_id", "book_count"]
    all_recs = all_recs.merge(book_titles, how="inner", on="book_id")
    all_recs["score"] = all_recs["book_count"] * (all_recs["book_count"] / all_recs["ratings"] * 2) #penalizes popularity so that the recs arent always books with the most ratings
    all_recs.sort_values("score", ascending=False).head(10)
    return all_recs


# func that calls all helper funcs to generate recs
def generate_recs():
    liked_books = generate_liked_books()
    book_mapping = create_book_mapping()
    overlap_users = get_overlap_users(liked_books, book_mapping)
    recs = create_recs(overlap_users, book_mapping)
    all_recs = update_recs(recs)
    popular_recs = all_recs[all_recs["book_count"] > 75].sort_values("score", ascending=False)
    best_recs = popular_recs[~popular_recs["book_id"].isin(liked_books)].head(25)
    
    bestRecsFilename = join(dirname(__file__), "best_recs.csv")
    best_recs.to_csv(bestRecsFilename)
    
    final_recs = pd.read_csv(bestRecsFilename)
    first_column = final_recs.columns[0]
    final_recs = final_recs.drop([first_column], axis=1)
    
    final_recs.pop('book_count')
    final_recs.pop('ratings')
    final_recs.pop('url')
    final_recs.pop('score')
    final_recs.pop('book_id') 
    
    finalRecsFilename = join(dirname(__file__), "final_recs.csv")
    final_recs_csv = final_recs.to_csv(finalRecsFilename)
    
    ratings_string = open(finalRecsFilename,"r")
    ratings_string = ' '.join([i for i in ratings_string])
    return ratings_string

