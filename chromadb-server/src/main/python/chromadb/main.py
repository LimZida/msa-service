import chromadb
from chromadb.config import Settings
from sentence_transformers import SentenceTransformer

# Chroma DB 클라이언트 설정
client = chromadb.Client(Settings(
    chroma_db_impl="duckdb+parquet",
    persist_directory="./chroma_db"
))
collection = client.create_collection("documents")

# 벡터화 모델 설정
model = SentenceTransformer('all-MiniLM-L6-v2')

# 예제 문서 추가
documents = [
    "Machine learning is fascinating.",
    "Artificial intelligence and machine learning.",
    "Deep learning advances AI research."
]
vectors = [model.encode(doc).tolist() for doc in documents]
ids = ["doc1", "doc2", "doc3"]
collection.add(documents=documents, embeddings=vectors, ids=ids)

# 쿼리 검색
query = "What is machine learning?"
query_vector = model.encode(query).tolist()
results = collection.query(query_embeddings=[query_vector], n_results=2)

# 검색 결과 출력
print("Search Results:")
for result in results['documents'][0]:
    print(result)

# import sqlite3
# print(sqlite3.sqlite_version)