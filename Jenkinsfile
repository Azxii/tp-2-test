pipeline {
    agent any

    stages {
        // 2.1 La phase Testtt
        stage('Test') {
            steps {
                // Use 'bat' for Windows and remove './'
                bat 'gradlew test'
            }
            post {
                always {
                    // 2. Archivage des résultats des tests unitaires
                    junit 'build/test-results/test/*.xml'
                    // 3. Génération des rapports de tests Cucumber
                    cucumber 'build/reports/cucumber/*.json'
                }
            }
        }
    }
}