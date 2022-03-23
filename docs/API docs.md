
# Medium Spring API

A RESTful API that crawls medium.com for trending articles, authors, and topics.

API endpoint: [http://medium.alexmehta.xyz](http://medium.alexmehta.xyz/)

## API Reference

#### Get list of top 6 trending articles from homepage

```http
  GET /api/v1/trending/articles
```

#### Get item

```http
  GET /api/v1/trending/articles
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of item to fetch |


#### Request article from API

```http
  POST /api/v1/scrape
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `url`      | `string` | **Required**. URL of article to fetch|

#### Example
```http
    POST /api/v1/scrape?url=https://gen.medium.com/can-we-overcome-the-death-of-expertise-6822d62f5cdf
```
