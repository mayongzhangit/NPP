/**
 * 1）打开摄像头，并开始播放
 * MDN https://developer.mozilla.org/zh-CN/docs/Web/API/MediaDevices/getUserMedia
 * https://developer.mozilla.org/zh-CN/docs/Web/API/Navigator/getUserMedia 这个创建失败
 */
function openCamera(){
    // 想要获取一个最接近 1280x720 的相机分辨率
    var constraints = { audio: true, video: { width: 800, height: 720 } };

    navigator.mediaDevices.getUserMedia(constraints)
        .then(function(mediaStream) {
            var video = document.getElementById('zhu-bo-video');
            video.srcObject = mediaStream;
            video.onloadedmetadata = function(e) {
                video.play();
            };

        })
        .catch(function(err) {
            console.log(err.name + ": " + err.message);
        });
}

/** @type {HTMLCanvasElement} */
var zhuBoVideo = document.getElementById("zhu-bo-video");
var zhuBoCanvas = document.getElementById("zhu-bo-canvas");
var ctx = zhuBoCanvas.getContext('2d');
var interval;
/**
 * 使用canvas画图，图像来源于video的截屏，定时截屏画到canvas中
 * canvas使用文档：https://developer.mozilla.org/zh-CN/docs/Web/API/Canvas_API/Tutorial/Using_images
 */
function drawFromVideo(){
    clearInterval(interval);
    interval = setInterval(function(){
        ctx.drawImage(zhuBoVideo,zhuBoVideo.width,zhuBoVideo.height);
        ws.send(zhuBoVideo.toDataURL());
    },50);
}

/**
 * websocket
 *
 */
var ws = null;
function connect() {
    var target = "ws://localhost:8082/live-endpoint";
    if ('WebSocket' in window) {
        ws = new WebSocket(target);
    } else if ('MozWebSocket' in window) {
        ws = new MozWebSocket(target);
    } else {
        alert('WebSocket is not supported by this browser.');
        return;
    }
    ws.onopen = function () {
        log('Info: WebSocket connection opened.');
        drawFromVideo();
    };
    ws.onmessage = function (event) {
        log('Received: ' + event.data);
    };
    ws.onclose = function (event) {
        log('Info: WebSocket connection closed, Code: ' + event.code + (event.reason == "" ? "" : ", Reason: " + event.reason));
    };
}

connect();

function log(message) {
    var console = document.getElementById('console');
    var p = document.createElement('p');
    p.style.wordWrap = 'break-word';
    p.appendChild(document.createTextNode(message));
    console.appendChild(p);
    while (console.childNodes.length > 25) {
        console.removeChild(console.firstChild);
    }
    console.scrollTop = console.scrollHeight;
}