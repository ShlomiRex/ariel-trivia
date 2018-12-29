# ariel-trivia

| <img src="github_images/trivia_icon.png" width="200" height="200"/> | <img src="github_images/home.png" width="100" height="200"/> |
<img src="github_images/questions.png" width="100" height="200"/>
<img src="github_images/question.png" width="100" height="200"/>
<img src="github_images/success.png" width="100" height="200"/>
<img src="github_images/post_trivia.png" width="100" height="200"/>











### Folders:

dev: This is tmp folder for developing and putting common files.
app: This is the android application: Android Studio project. Open it with Android Studio.
server: This is the main middleware server that links the android app to the mongodb server.
server/sketches: This folder contains useful sketches that describe the flow of events.

### Git commands:

$ git status
This is the best tool for checking staging / conflicts / commits / pulls ect.
I personally use it a lot (~Shlomi)

$ git add .
This adds all files (the '.' is path of the project) to a stage.
A stage holds the files to be changed, modified, removed and ect. untill next stage.

$ git commit -m "Type your message here"
This commits (to your local repository) and says "I'm done with coding. My version is stable". This prepares the local repository to be pushed into remote repository(github).

$ git push
This pushes all the commits that you have done. This pushes all changes to the remote repo (github)
This is final act.

### More functions:

$ git pull
This is very important: Developer 1 clones remote repo. Developer 2 clones remote repo. Developer 1 fixes bugs. He finishes and pushes changes to remote repo.
Developer 2, unaware of the changes done by developer 1, is still coding with the 'old version' of the repository.
Therefor, before pushing developer 2 changes, he must 'pull' the new version.

### Mongo shell commands:

Start server:
$ mongod --bind_ip 127.0.0.1 --port 27017 --dbpath .

Connect to local mongo server:
$ mongo --port 27017

Export your database (make sure server is running): 
$ mongodump -d <database name> -o <path to export to>

To restore / import database:
mongorestore -d <database_name> <directory_backup>
Taken from: https://stackoverflow.com/a/16605781/5854499
