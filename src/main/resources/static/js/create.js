const loadFile = (event) => {
  var reader = new FileReader();
  reader.onload = function () {
    var output = document.getElementById("profile-pic-preview");
    output.src = reader.result;
    output.style.display = "block";
    var label = document.getElementById('profile-pic-label');
    label.style.display = 'none';
  };
  reader.readAsDataURL(event.target.files[0]);
};

const cancel = () => {
  window.location.href = "/employees";
};
