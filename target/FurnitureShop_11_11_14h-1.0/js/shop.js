const buttonPaginations = document.querySelectorAll("[data-pagination]");
if (buttonPaginations){
    let url = new URL (window.location.href);
    console.log(url);
    buttonPaginations.forEach((button)=>{
        button.addEventListener("click",()=>{
            const page = button.getAttribute("data-pagination");
            console.log(page);
            url.searchParams.set("page",page);
            window.location.href = url.href;
        })
    })
}
