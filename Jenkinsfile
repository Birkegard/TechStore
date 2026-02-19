pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh './mvnw clean package -Dspring.profiles.active=test'
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