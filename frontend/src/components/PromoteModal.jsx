import './PromoteModal.css';

const PromoteModal = ({
  show,
  onClose,
  specialties,
  selectedSpecialties,
  onSpecialtyChange,
  onPromote,
}) => {
  if (!show) return null;

  const handleCheckboxChange = (specialtyId) => (e) => {
    if (e.target.checked) {
      onSpecialtyChange([...selectedSpecialties, specialtyId]);
    } else {
      onSpecialtyChange(selectedSpecialties.filter((id) => id !== specialtyId));
    }
  };

  return (
    <div className="modal-overlay">
      <div className="promote-modal">
        <h3>Select Specialties</h3>
        <div className="specialties-grid">
          {specialties.map((specialty) => (
            <label key={specialty.id} className="checkbox-label">
              <input
                type="checkbox"
                checked={selectedSpecialties.includes(specialty.id)}
                onChange={handleCheckboxChange(specialty.id)}
              />
              <span>{specialty.name}</span>
            </label>
          ))}
        </div>
        <div className="modal-buttons">
          <button
            className="cancel-button"
            onClick={() => {
              onClose();
              onSpecialtyChange([]);
            }}
          >
            Cancel
          </button>
          <button
            className="confirm-button"
            onClick={onPromote}
            disabled={selectedSpecialties.length === 0}
          >
            Confirm Promotion ({selectedSpecialties.length})
          </button>
        </div>
      </div>
    </div>
  );
};

export default PromoteModal;
