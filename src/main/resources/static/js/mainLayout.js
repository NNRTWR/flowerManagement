document.addEventListener('DOMContentLoaded', function() {
    const drawerToggle = document.getElementById('my-drawer');
    const content = document.getElementById('content-layout');

    //default
    content.style.width = 'calc(100% - 255px)';
    content.style.marginLeft = '255px';
    drawerToggle.style.zIndex = '0'
    content.style.zIndex = '10';

    drawerToggle.addEventListener('change', function() {
        if (drawerToggle.checked) {
            //sidebar เปิด
            content.style.width = 'calc(100% - 255px)'; // 64px เป็นขนาดของ sidebar
            content.style.marginLeft = '255px';
            // content.style.zIndex = '0';
        } else {
            // Sidebar ปิด
            content.style.width = '100%';
            content.style.marginLeft = '0';
            // content.style.zIndex = '10';
        }
    });
});