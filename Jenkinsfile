pipeline {
    agent any

    stages {
        [cite_start]// 2.1 La phase Test [cite: 22]
        stage('Test') {
            steps {
                [cite_start]// 1. Lancement des tests unitaires [cite: 24]
                // "bat" is used for Windows compatibility
                bat 'gradlew test'
            }
            post {
                always {
                    [cite_start]// 2. Archivage des résultats des tests unitaires [cite: 25]
                    junit 'build/test-results/test/*.xml'

                    [cite_start]// 3. Génération des rapports de tests Cucumber [cite: 26]
                    // Requires 'Cucumber reports' plugin in Jenkins
                    cucumber 'build/reports/cucumber/*.json'
                }
            }
        }

        [cite_start]// 2.2 La phase Code Analysis [cite: 27]
        stage('Code Analysis') {
            steps {
                [cite_start]// "sonar" is the server name defined in Jenkins config [cite: 130]
                withSonarQubeEnv('sonar') {
                    [cite_start]// Analyse la qualité du code avec SonarQube [cite: 28]
                    bat 'gradlew sonarqube'
                }
            }
        }

        [cite_start]// 2.3 La phase Code Quality [cite: 29]
        stage('Code Quality') {
            steps {
                // Wait for SonarQube webhook response
                timeout(time: 1, unit: 'HOURS') {
                    [cite_start]// Stop pipeline if Quality Gate is Failed [cite: 30, 31]
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        [cite_start]// 2.4 La phase Build [cite: 32]
        stage('Build') {
            steps {
                [cite_start]// 1. Génération du fichier Jar [cite: 34]
                bat 'gradlew assemble'

                [cite_start]// 2. Génération de la documentation [cite: 35]
                bat 'gradlew javadoc'
            }
            post {
                success {
                    [cite_start]// 3. Archivage du fichier Jar et de la documentation [cite: 36]
                    archiveArtifacts artifacts: 'build/libs/*.jar, build/docs/javadoc/**'
                }
            }
        }

        [cite_start]// 2.5 La phase Deploy [cite: 37]
        stage('Deploy') {
            steps {
                // Déployer le fichier Jar dans mymavenrepo.com
                bat 'gradlew publish'
            }
        }
    }

    [cite_start]// 2.6 La phase Notification [cite: 40]
    post {
        success {
            [cite_start]// Notification par mail [cite: 42]
            mail to: 'kh_benferhat@esi.dz', // Update with your team's email
                 subject: "Success: ${currentBuild.fullDisplayName}",
                 body: "The build and deploy were successful."

            [cite_start]// Notification sur Slack [cite: 42]
            // Requires 'Slack Notification' plugin configuration
            slackSend channel: '#dev-team', message: "Build Success: ${currentBuild.fullDisplayName}"
        }
        failure {
            [cite_start]// En cas d'échec [cite: 43]
            mail to: 'kh_benferhat@esi.dz',
                 subject: "Failed: ${currentBuild.fullDisplayName}",
                 body: "The pipeline failed in stage: ${env.STAGE_NAME}"

            slackSend channel: '#dev-team', color: 'danger', message: "Build Failed: ${currentBuild.fullDisplayName}"
        }
    }
}