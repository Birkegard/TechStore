pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                bat 'mvnw.cmd clean package -Dspring.profiles.active=test'
            }
        }
    }

    post {
        success {
            echo 'Bygget lyckades!'
        }
        failure {
            echo 'Bygget misslyckades!'
        }
    }
}