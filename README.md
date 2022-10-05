# web-server

```mermaid
sequenceDiagram
    participant Client
    participant Server
    Client->>Server: Request
    Server->>Server: Request解析
    Server->>Server: Resource読み取り
    Server->>Server: Resposne生成
    Server-->>Client: Reponse
```

## HTTP request/response sample
```http
GET /mypage.html HTTP/1.0
User-Agent: NCSA_Mosaic/2.0 (Windows 3.1)
```
```http
200 OK
Date: Tue, 15 Nov 1994 08:12:31 GMT
Server: CERN/3.0 libwww/2.17
Content-Type: text/html
<HTML>
A page with an image
  <IMG SRC="/myimage.gif">
</HTML>
```