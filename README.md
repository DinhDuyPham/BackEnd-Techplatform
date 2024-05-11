http://150.95.109.144:8080/swagger-ui/index.html

# CheckList deploy
1. Clean package
    ```shell
       mvn clean package -DskipTests    
    ```
2. Run docker
    ```shell
      docker compose up -d --build  
    ```