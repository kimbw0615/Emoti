/*!
* Start Bootstrap - Full Width Pics v5.0.6 (https://startbootstrap.com/template/full-width-pics)
* Copyright 2013-2023 Start Bootstrap
* Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-full-width-pics/blob/master/LICENSE)
*/
// This file is intentionally blank
// Use this file to add JavaScript to your project

function animateValue(id, start, end, duration) {
    let range = end - start;
    let current = start;
    let increment = end > start? 1 : -1;
    let stepTime = Math.abs(Math.floor(duration / range));
    let obj = document.getElementById(id);
    let timer = setInterval(function() {
        current += increment;
        obj.innerHTML = current;
        if (current == end) {
            clearInterval(timer);
        }
    }, stepTime);
}

window.onload = function() {
    animateValue("emotion-count", 0, 150, 2000); // 0에서 150까지 2초 동안 증가
    animateValue("post-count", 0, 5, 3000); // 0에서 5까지 2초 동안 증가
    animateValue("comment-count", 0, 2000, 50); // 0에서 2000까지 2초 동안 증가
};

