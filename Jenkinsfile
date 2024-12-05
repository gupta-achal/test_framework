pipeline{
    agent {
        docker{
            image "alpine"
        }
    }

    stages{
        stage(stage 1){
            steps{
                echo 'hi'
            }
        }

    }
    post{
        always{
            echo "always"
        }
    }
}