# Jumia Service Exercise project

# Build Docker Image 
1. open command line terminal
2. Go to /Exercise/target/ to locate "jumia-exercise.jar" image.
3. type down: 
$ docker build -t jumia-exercise.jar . 

# Check Docker Image 
$ docker image ls

# Run Docker Image 
$ docker run -p 9090:8080 jumia-exercise.jar

In the run command, I have specified that the port 8080 on the container should be mapped to the port 9090 on the Host OS.

# Run Application 
1. Either user docker Desktop's OPEN IN BROWSER
2. or open browser and type rul "http://localhost:9090/"

<img width="1096" alt="docker screenshot" src="https://user-images.githubusercontent.com/6859014/132523416-9c3f8e16-6a20-4348-b0a2-1f26de15d833.png">
