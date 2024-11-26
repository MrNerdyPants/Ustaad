# Ustaad Search Engine - Indexer Service

The **Indexer Service** is a core component of the Ustaad Search Engine, designed to process crawled data, tokenize content, remove stop words, and create an efficient inverted index for quick search lookups. This service interacts with other microservices like the **Crawler**, **Ranker**, and **Query Processor** to deliver a seamless and fast search experience.

---

## Features

- **Tokenization:** Extracts meaningful tokens from raw crawled data.
- **Stop-word Removal:** Filters out common words to improve search efficiency.
- **Stemming:** Reduces tokens to their root forms for better matching.
- **Index Creation:** Builds and maintains an inverted index for quick lookups.
- **Scalability:** Handles large datasets efficiently using Apache Lucene and modern indexing techniques.
- **Interoperability:** Works with other Ustaad Search Engine microservices to create a cohesive search pipeline.

---

## Technology Stack

- **Java**: Core programming language.
- **Apache Lucene**: For tokenization, stop-word removal, and stemming.
- **Spring Boot**: Framework for building and exposing REST APIs.
- **PostgreSQL**: Database for storing indexed data.
- **Docker**: Containerization for seamless deployment.
- **RabbitMQ**: Messaging service to integrate with other microservices.

---

## Indexer Workflow

1. **Receive Crawled Data:**  
   Data is sent to the Indexer Service by the Crawler Service via RabbitMQ or direct API calls.

2. **Process Content:**
    - Parse the content to extract relevant fields (e.g., title, meta description, main content).
    - Tokenize the text using Apache Lucene.
    - Apply stop-word removal and stemming.

3. **Create and Update Index:**
    - Construct an inverted index mapping terms to their document IDs and metadata.
    - Store the index in PostgreSQL for persistence.

4. **Publish Updates:**
    - Notify the Ranker Service about newly indexed content.
    - Provide APIs for the Query Processor to search the index efficiently.

---

## API Endpoints

### 1. **Index Content**
**POST** `/api/index`
- **Description:** Index a single document.
- **Request Body:**
```json
{
  "url": "http://example.com",
  "title": "Sample Title",
  "metaDescription": "This is a meta description.",
  "content": "Main content of the page.",
  "language": "en"
}
```
- **Response:**
```json
{
  "status": "success",
  "message": "Document indexed successfully."
}
```

---

### 2. **Search Index**
**GET** `/api/search`
- **Description:** Query the index.
- **Query Parameters:**
    - `q`: Search query.
- **Response:**
```json
{
  "results": [
    {
      "url": "http://example.com",
      "title": "Sample Title",
      "snippet": "Snippet of the content containing the query."
    }
  ]
}
```

---

## Environment Variables

| Variable Name           | Description                      | Default Value          |  
|--------------------------|----------------------------------|------------------------|  
| `DATABASE_URL`           | PostgreSQL connection string    | `jdbc:postgresql://localhost:5432/ustaad_index` |  
| `RABBITMQ_HOST`          | RabbitMQ host                   | `localhost`            |  
| `STOP_WORDS_FILE`        | File path for stop words         | `resources/stopwords.txt` |  

---

## Integration with Ustaad Search Engine

1. **Crawler Service:**
    - Provides raw content for indexing.
    - Communicates with the Indexer via RabbitMQ or REST API.

2. **Ranker Service:**
    - Receives updates about newly indexed documents.
    - Uses indexed metadata for ranking algorithms.

3. **Query Processor:**
    - Queries the Indexer to retrieve documents matching search terms.
    - Renders results to the user.

---

## Running the Service

1. **Clone the repository:**
```bash  
git clone https://github.com/ustaad/indexer-service.git  
cd indexer-service  
```  

2. **Build and Run:**
```bash  
./mvnw clean package  
java -jar target/indexer-service.jar  
```  

3. **Run with Docker:**
```bash  
docker build -t ustaad-indexer .  
docker run -p 8080:8080 ustaad-indexer  
```  

---

## Future Improvements

- Implement distributed indexing for scalability.
- Add support for multiple languages and custom analyzers.
- Enhance indexing for multimedia and non-text content.

---

**Ustaad** - **Empowering Knowledge, One Search at a Time.**