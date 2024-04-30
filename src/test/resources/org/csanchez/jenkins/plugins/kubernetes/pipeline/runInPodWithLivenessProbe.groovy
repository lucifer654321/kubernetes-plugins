podTemplate(volumes: [emptyDirVolume(mountPath: '/my-mount')], containers: [
        containerTemplate(name: 'busybox', image: 'busybox', ttyEnabled: true, command: 'cat', livenessProbe: containerLivenessProbe( execArgs: 'uname -a', initialDelaySeconds: 5, timeoutSeconds: 1, failureThreshold: 3, periodSeconds: 10, successThreshold: 1))
]) {

    node(POD_LABEL) {
        stage('Wait for Liveness Probe') {
            container('busybox') {
                sh 'sleep 6 && echo "Still alive"'
            }
        }
    }
}
