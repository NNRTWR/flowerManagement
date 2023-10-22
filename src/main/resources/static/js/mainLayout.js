document.addEventListener('DOMContentLoaded', function() {
    const drawerToggle = document.getElementById('my-drawer');
    const content = document.getElementById('content-layout');
    const nav = document.getElementById('nav');
    const side = document.getElementById('side');
    //default
    window.scrollTo(0, 0);
    content.style.width = 'calc(100% - 256px)';
    content.style.marginLeft = '256px';
    //drawerToggle.style.zIndex = '20'
    content.style.zIndex = '10';
    nav.style.zIndex = '20'
    side.style.zIndex = '10'

    drawerToggle.addEventListener('change', function() {
        if (drawerToggle.checked) {
            //sidebar เปิด
            content.style.width = 'calc(100% - 256px)'; // 64px เป็นขนาดของ sidebar
            content.style.marginLeft = '256px';
        } else {
            // Sidebar ปิด
            content.style.width = '100%';
            content.style.marginLeft = '0';
            
        }
    });
    window.onbeforeunload = function() {
        window.scrollTo(0, 0); 
    };
});