package main.service;

import java.util.ArrayList;
import java.util.List;

import main.dao.MedStaffDAO;
import main.exceptions.DAOException;
import main.exceptions.ServiceException;
import main.model.MedStaff;


public class MedStaffSVC {
	private MedStaffDAO medStaffDAO;
	

	public MedStaffSVC() {
		medStaffDAO = new MedStaffDAO();
		
	}

	public List<MedStaff> findAll() throws ServiceException {

		List<MedStaff> list = new ArrayList<MedStaff>();
		try {
			list = medStaffDAO.findAll();
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}

		return list;
	}
	
	public List<MedStaff> findAllDocs() throws ServiceException {

		List<MedStaff> list = new ArrayList<MedStaff>();
		try {
			list = medStaffDAO.findAllMedStaffOfClass("doc");
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}

		return list;
	}
	
	public List<MedStaff> findAllNurses() throws ServiceException {

		List<MedStaff> list = new ArrayList<MedStaff>();
		try {
			list = medStaffDAO.findAllMedStaffOfClass("nur");
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}

		return list;
	}
	
	public MedStaff findMedStaffByMedId(String medId) throws ServiceException {

		MedStaff medStaff = new MedStaff();
		try {
			medStaff = medStaffDAO.findMedStaffByMedId(medId);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}

		return medStaff;
	}
	
	public MedStaff findMedStaffByNhsNo(Long nhsNo) throws ServiceException {

		MedStaff medStaff = new MedStaff();
		try {
			medStaff = medStaffDAO.findMedStaffByNhsNo(nhsNo);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}

		return medStaff;
	}

	public void deleteByMedId(MedStaff medStaff) throws ServiceException {
		try {
			medStaffDAO.deleteByMedId(medStaff);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	public boolean insert(MedStaff medStaff) throws ServiceException {
		try {
			
			// Insertamos primero en medStuff por PK
			medStaffDAO.insert(medStaff);
			
			
			
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
		
		return true;
	}
	
	public boolean updateMedStaff(MedStaff medStaff) throws ServiceException {

		try {
			if (medStaffDAO.updateMedStaff(medStaff) == 1) {
				System.out.println("user auth svc");
				return true;
			}
		} catch (DAOException e) {
			e.printStackTrace();
			throw (new ServiceException());
		}

		return false;
	}

}
