// 기본 자바스크립트 파일
// 필요한 클라이언트 측 기능 구현

document.addEventListener('DOMContentLoaded', function() {
    console.log('Spring Boot Boilerplate loaded successfully');
    
    // 삭제 버튼에 확인 대화상자 추가
    const deleteButtons = document.querySelectorAll('.delete-btn');
    deleteButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            if (!confirm('정말로 이 항목을 삭제하시겠습니까?')) {
                e.preventDefault();
            }
        });
    });
});
