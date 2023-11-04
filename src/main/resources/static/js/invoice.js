document.addEventListener("DOMContentLoaded", function() {
    document.getElementById("confirmButton").addEventListener("click", function(event) {
        var selectedOption = document.getElementById("order_id").value;
        if (selectedOption === "0") {
            event.preventDefault(); 
        }
    });

    document.getElementById("invoiceForm").addEventListener("submit", function (event) {
        event.preventDefault(); 
        let selectedOrderId = document.getElementById("order_id").value;
        let selectedOption = document.querySelector('option[value="' + selectedOrderId + '"]');
        let selectedFID = selectedOption.getAttribute("data-fid");
        let selectedOID = selectedOption.value;

        let fidInput = document.createElement("input");
        fidInput.type = "hidden";
        fidInput.name = "FID"; 
        fidInput.value = selectedFID;

        let oidInput = document.createElement("input");
        oidInput.type = "hidden";
        oidInput.name = "OID"; 
        oidInput.value = selectedOID;

        let form = document.getElementById("invoiceForm");
        form.appendChild(fidInput);
        form.appendChild(oidInput);
        form.submit();
    });

    let status = document.getElementById("status").textContent.trim(); 
    let completeButton = document.getElementById("completeButton");

    if (status === "CANCELED" || status === "COMPLETED" || parseInt(document.getElementById("quantity").textContent) > parseInt(document.getElementById("remain").textContent) || status==="") {
        completeButton.disabled = true;
    } else {
        completeButton.disabled = false;
    }
});
