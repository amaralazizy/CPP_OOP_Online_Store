#include "baseEntity.h"

BaseEntity::BaseEntity(){}
BaseEntity::BaseEntity(int _id): id(_id){}

int BaseEntity::getId() const {
	return id;
}

void BaseEntity::setId(int id) {
	this->id = id;
}