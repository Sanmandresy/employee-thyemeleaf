const loadNewFile = (event) => {
  var reader = new FileReader();
  var actual = document.getElementById("actual-pic");
  reader.onload = function () {
    var output = document.getElementById("new-pic-preview");
    output.src = reader.result;
    output.style.display = "block";
    actual.remove();
    var label = document.getElementById("new-pic-label");
    label.style.display = "none";
  };
  reader.readAsDataURL(event.target.files[0]);
};

const cancel = () => {
  window.location.href = "/employees";
};
