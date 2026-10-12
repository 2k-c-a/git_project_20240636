const box = document.getElementById('box');

if (box) {
    const originalText = box.textContent;
    const originalBackgroundImage = box.style.backgroundImage;

    box.addEventListener('mouseover', function () {
        box.textContent = '';
        box.style.backgroundImage = 'url("' + box.dataset.hoverImage + '")';
    });

    box.addEventListener('mouseout', function () {
        box.textContent = originalText;
        box.style.backgroundImage = originalBackgroundImage;
    });
}

// 일반 버튼, 버튼 모양 링크, 메뉴 버튼에 공통 효과를 적용합니다.
document.querySelectorAll('button, a.button, nav a, [role="button"]').forEach(function (button) {
    let originalBackgroundImage;
    let originalColor;

    button.addEventListener('mouseenter', function () {
        originalBackgroundImage = button.style.backgroundImage;
        originalColor = button.style.color;
        button.style.backgroundImage = 'linear-gradient(135deg, #24587e, #7048b5)';
        button.style.color = 'white';
    });

    button.addEventListener('mouseleave', function () {
        button.style.backgroundImage = originalBackgroundImage;
        button.style.color = originalColor;
    });
});
