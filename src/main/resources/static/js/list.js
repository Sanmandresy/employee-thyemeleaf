const redirectToEmployee = (employeeId) => {
  window.location.href = "/employee?id=" + employeeId;
};

// In case we want to download from the front
const downloadCSV = (csv, filename) => {
  var csvFile;
  var downloadLink;

  csvFile = new Blob([csv], { type: "text/csv" });

  downloadLink = document.createElement("a");

  downloadLink.download = filename;

  downloadLink.href = window.URL.createObjectURL(csvFile);

  document.body.appendChild(downloadLink);
  downloadLink.click();
};

const exportToCSV = () => {
  const displayedEmployeesIds = Array.from(
      document.querySelectorAll(".employee-row")
  ).map((row) => row.getAttribute("data-name"));
  document.getElementById("employeeIds").value =
    displayedEmployeesIds.join(",");
  document.getElementById("csv-form").submit();
};
